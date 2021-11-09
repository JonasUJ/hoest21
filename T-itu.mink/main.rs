/*
 * AC(100) solution using Edmonds-Karp to find the maximum flow for the Pit Optimization problem.
 * There's still a lot of room for improvement - I only aimed to make it work.
 */

use std::{
    cell::RefCell,
    collections::{HashSet, VecDeque},
    hash::Hash,
    io::{self, BufRead, Write},
};

#[derive(Clone)]
enum Element {
    Dirt(usize, isize),
    Mink(usize, isize, char),
}

impl Element {
    fn text(self, excavated: &HashSet<usize>) -> u8 {
        match self {
            Element::Dirt(id, _) => {
                if excavated.contains(&id) {
                    ' ' as u8
                } else {
                    '#' as u8
                }
            }
            Element::Mink(id, _, c) => {
                if excavated.contains(&id) {
                    ' ' as u8
                } else {
                    c as u8
                }
            }
        }
    }
}

impl Default for Element {
    fn default() -> Self {
        Element::Dirt(std::usize::MAX, 0)
    }
}

#[derive(Eq)]
struct MinkNode {
    x: usize,
    y: usize,
    id: usize,
    cost: isize,
    ancestors: HashSet<usize>,
    all_ancestors: HashSet<usize>,
}

impl MinkNode {
    fn new(x: usize, y: usize, id: usize, cost: isize) -> Self {
        MinkNode {
            x,
            y,
            id,
            cost,
            ancestors: HashSet::<usize>::new(),
            all_ancestors: HashSet::<usize>::new(),
        }
    }
}

impl PartialEq for MinkNode {
    fn eq(&self, other: &Self) -> bool {
        self.id == other.id
    }
}

impl Hash for MinkNode {
    fn hash<H: std::hash::Hasher>(&self, state: &mut H) {
        state.write_usize(self.id);
        state.finish();
    }
}

#[allow(unused_must_use)]
fn main() {
    let stdin = io::stdin();
    let mut input = String::new();
    stdin.read_line(&mut input);
    let nums: Vec<usize> = input
        .split_whitespace()
        .map(|n| n.parse::<usize>().unwrap())
        .collect();
    let b = nums[1];
    let d = nums[2] as isize;

    let mut mink = Vec::<RefCell<MinkNode>>::new();
    let mut view: Vec<Element> = vec![Default::default(); b];
    let mut ground: Vec<Vec<Element>> = stdin
        .lock()
        .lines()
        .enumerate()
        .map(|(y, l)| {
            l.unwrap()
                .chars()
                .enumerate()
                .map(|(x, c)| {
                    let el = match c {
                        '#' => match view[x] {
                            Element::Dirt(id, cost) => Element::Dirt(id, cost + 1),
                            Element::Mink(id, cost, _) => Element::Mink(id, cost + 1, '#'),
                        },
                        '<' => match view[x] {
                            Element::Dirt(_, cost) => {
                                let id = mink.len();
                                let m = MinkNode::new(x, y, id, cost - d);
                                mink.push(RefCell::new(m));
                                Element::Mink(id, 0, c)
                            }
                            Element::Mink(id, cost, _) => {
                                let new_id = mink.len();
                                let mut m = MinkNode::new(x, y, new_id, cost - d);
                                let other = mink[id].borrow();
                                m.all_ancestors = other.all_ancestors.clone();
                                m.all_ancestors.insert(other.id);
                                m.ancestors.insert(other.id);
                                drop(other);
                                mink.push(RefCell::new(m));
                                Element::Mink(new_id, 0, c)
                            }
                        },
                        _ => match view[x] {
                            Element::Dirt(_, cost) => {
                                let mut m = mink.last().unwrap().borrow_mut();
                                m.cost += cost;
                                Element::Mink(m.id, 0, c)
                            }
                            Element::Mink(id, cost, _) => {
                                let mut m = mink.last().unwrap().borrow_mut();
                                m.cost += cost;
                                let other = mink[id].borrow();
                                m.all_ancestors = &m.all_ancestors | &other.all_ancestors;
                                m.all_ancestors.insert(other.id);
                                m.ancestors.insert(other.id);
                                m.ancestors = &m.ancestors - &other.all_ancestors;
                                Element::Mink(m.id, 0, c)
                            }
                        },
                    };
                    view[x] = el.clone();
                    match el {
                        Element::Mink(_, _, '#') => Default::default(),
                        _ => el,
                    }
                })
                .collect()
        })
        .collect();

    for m in &mink {
        let m = m.borrow();
        for offset in 0..3 {
            for y in (0..m.y).rev() {
                ground[y][m.x + offset] = match ground[y][m.x + offset] {
                    Element::Dirt(_, _) => Element::Dirt(m.id, 0),
                    _ => break,
                }
            }
        }
    }

    let source = mink.len();
    let sink = mink.len() + 1;
    let mut source_row = Vec::<usize>::new();
    let mut adj_mat: Vec<Vec<usize>> = vec![];

    for m in &mink {
        let m = m.borrow();
        let mut row = Vec::<usize>::new();
        for i in 0..mink.len() {
            row.push(match m.ancestors.get(&i) {
                Some(_) => std::usize::MAX,
                None => 0,
            });
        }
        row.push(0);
        if m.cost < 0 {
            row.push(0);
            source_row.push(-m.cost as usize);
        } else {
            row.push(m.cost as usize);
            source_row.push(0);
        }
        adj_mat.push(row);
    }
    source_row.push(0);
    source_row.push(0);
    adj_mat.push(source_row);
    adj_mat.push(vec![0; adj_mat.len() + 1]);

    while let Some(path) = find_path(source, sink, &adj_mat) {
        let mut flow = std::usize::MAX;
        let mut cur = sink;
        while cur != source {
            let other = adj_mat[path[cur]][cur];
            if other < flow {
                flow = other;
            }
            cur = path[cur];
        }
        cur = sink;
        while cur != source {
            let next = path[cur];
            adj_mat[next][cur] -= flow;
            adj_mat[cur][next] += flow;
            cur = next;
        }
    }

    let mut to_excavate = HashSet::<usize>::new();
    all_connected(source, &adj_mat, &mut to_excavate);

    let mut stdout = io::stdout();
    let mut buf = [0; 201];
    buf[b] = '\n' as u8;
    for v in ground {
        for (i, e) in v.into_iter().enumerate() {
            buf[i] = e.text(&to_excavate);
        }
        stdout.write_all(&buf[..=b]);
    }
}

fn find_path(from: usize, to: usize, adj_mat: &Vec<Vec<usize>>) -> Option<Vec<usize>> {
    let mut visited = HashSet::<usize>::new();
    let mut queue = VecDeque::<usize>::new();
    let mut path: Vec<usize> = vec![8; adj_mat.len()];

    queue.push_back(from);
    visited.insert(from);

    while let Some(node) = queue.pop_front() {
        if node == to {
            return Some(path);
        }
        for (i, flow) in adj_mat[node].iter().enumerate() {
            if flow > &0 && !visited.contains(&i) {
                queue.push_back(i);
                visited.insert(i);
                path[i] = node;
            }
        }
    }

    None
}

fn all_connected(node: usize, adj_mat: &Vec<Vec<usize>>, visited: &mut HashSet<usize>) {
    visited.insert(node);
    for i in 0..adj_mat.len() {
        if adj_mat[node][i] > 0 && !visited.contains(&i) {
            all_connected(i, adj_mat, visited);
        }
    }
}
