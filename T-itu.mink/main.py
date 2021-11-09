"""
94% solution that works by finding leafs with negative profit and cutting them off. The process of
pruning leafs is repeated until no more leafs can be removed. It currently falls short of AC(100)
because individual nodes only know about their direct dependants, and they'd need to know about
*all* dependants to accuratly calculate their own profit. Naïvely implementing this resulted in a 
running time greater than the one second limit imposed by the challenge.
"""

import sys
import abc
from collections import deque
from functools import reduce
from typing import Set, Optional


class Element(abc.ABC):
    __slots__ = "excavated", "x", "y"

    excavated: bool
    x: int
    y: int

    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.excavated = False

    @abc.abstractmethod
    def text():
        return

    @abc.abstractmethod
    def excavate():
        return


class Dirt(Element):
    def text(self):
        return " " if self.excavated else "#"

    def excavate(self):
        self.excavated = True


class Mink(Element):
    __slots__ = "calls", "filler", "dependencies", "dependants", "d", "_cost"

    chars = "<", "=", ">"
    calls: int
    d: int
    _cost: Optional[int]
    filler: Set[Dirt]
    dependencies: Set["Mink"]
    dependants: Set["Mink"]

    def __init__(self, x, y, d):
        super().__init__(x, y)
        self.d = d
        self._cost = None
        self.calls = -1
        self.filler = set()
        self.dependencies = set()
        self.dependants = set()

    def text(self):
        if self.excavated:
            return " "
        self.calls += 1
        return self.chars[self.calls % len(self.chars)]

    def excavate(self):
        self.excavated = True
        for dirt in self.filler:
            dirt.excavate()

    def cost(self):
        if self._cost is None:
            self._cost = (
                len(self.filler)
                + reduce(lambda a, m: a + m.cost(), self.dependants, 0)
                - self.d
            )
        return self._cost

    def expand(self):
        yield self
        for dependant in self.dependants:
            yield from dependant.expand()

    def remove(self, ms):
        self._cost = None
        if not self.dependants.isdisjoint(ms):
            self.dependants -= ms

    def __eq__(self, other):
        return isinstance(other, self.__class__) and hash(self) == hash(other)

    def __hash__(self):
        return (self.x << 16) + self.y

    def __repr__(self):
        return f"Mink({self.x}, {self.y}, cost={self.cost()})"


class Cluster:
    __slots__ = "mink", "cost"

    mink: Set[Mink]
    cost: int

    def __init__(self, mink: Set[Mink]):
        self.mink = mink
        self.cost = reduce(lambda a, m: a + len(m.filler) - m.d, self.mink, 0)

    def excavatable(self):
        return self.cost < 0

    def shrink(self):
        yield from self.eliminate(
            set(
                d
                for m in filter(
                    lambda m: not any(map(lambda d: d.cost() >= 0, m.dependants))
                    and m.cost() >= 0,
                    self.mink,
                )
                for d in m.expand()
            )
        )

    def eliminate(self, ms):
        mink = self.mink - ms
        for m in mink:
            m.remove(ms)
        yield from make_clusters(mink)

    def excavate(self):
        for m in self.mink:
            m.excavate()

    def __repr__(self):
        return f"Cluster({self.mink})"

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.mink == other.mink


def read_ground():
    h, b, d = map(int, sys.stdin.readline().split())
    mink = set()
    ground = []
    for y in range(h):
        ground.append([])
        for x in range(b):
            read = sys.stdin.read(1)
            if read == "#":
                ground[y].append(Dirt(x, y))
            elif read == "<":
                m = Mink(x, y, d)
                ground[y].append(m)
                mink.add(m)
            else:
                ground[y].append(ground[y][x - 1])
        sys.stdin.read(1)
    return ground, mink


def set_dependencies(ground, mink):
    for m in mink:
        for offset in range(3):
            for y in range(m.y - 1, -1, -1):
                here = ground[y][m.x + offset]
                if isinstance(here, Mink):
                    here.dependants.add(m)
                    m.dependencies.add(here)
                    break
                else:
                    m.filler.add(here)


def make_clusters(mink):
    seen = set()

    def connected(m):
        if m not in seen:
            seen.add(m)
            for other in m.dependencies | m.dependants:
                yield from connected(other)
            yield m

    for m in mink:
        if m not in seen:
            yield Cluster(set(connected(m)))


ground, mink = read_ground()
set_dependencies(ground, mink)
queue = deque(make_clusters(mink))

while len(queue) != 0:
    cluster = queue.popleft()
    for nc in cluster.shrink():
        if nc == cluster:
            if nc.excavatable():
                nc.excavate()
        else:
            queue.append(nc)

print("\n".join("".join(el.text() for el in row) for row in ground))
