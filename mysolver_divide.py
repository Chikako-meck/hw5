
import sys
import math
import itertools
import copy

from common import read_input, print_solution, overwrite_solution

def distance(city1, city2):
    return math.sqrt((city1[0] - city2[0]) ** 2 + (city1[1] - city2[1]) ** 2)


def differ(city1, city2, common):
    distance1 = distance(city1, common)
    distance2 = distance(city2, common)
    distance_12 = distance(city1, city2)
    return distance1 + distance2 - distance_12


def search(x, buff):
    for i in xrange(len(buff)):
        if buff[i] == x:
            if i == 0:
                return len(buff) - 1, i, i + 1
            if i == len(buff) - 1:
                return i - 1, i, 0
            return i - 1, i, i + 1

        
def make_new_path(cities, common, direction):
    path = []
    i = common + direction
    while True:
        if i < 0:
            i = len(cities) - 1
        elif i >= len(cities):
            i = 0
        if i == common:
            break
        path.append(cities[i])
        i += direction
    return path


def make_path_index(cities, path):
    path_index = []
    for i in range(len(path)):
        index = cities.index(path[i])
        path_index.append(index)
    return path_index

    
def permutation(cities):
    return list(itertools.permutations(cities))


def merge(first, second, common):
    before_common1, common1, after_common1 = search(common, first)
    before_common2, common2, after_common2 = search(common, second)
    d1 = differ(first[before_common1], second[before_common2], common)
    d2 = differ(first[before_common1], second[after_common2], common)
    d3 = differ(first[after_common1], second[before_common2], common)
    d4 = differ(first[after_common1], second[after_common2], common)

    max_d = max(d1, d2, d3, d4)
    if d1 == max_d:
        first[common1:common1] = make_new_path(second, common2, -1)
    elif d2 == max_d:
        first[common1:common1] = make_new_path(second, common2, 1)
    elif d3 == max_d:
        first[after_common1:after_common1] = make_new_path(second, common2, 1)
    else:
        first[after_common1:after_common1] = make_new_path(second, common2, -1)
    return first


def divide_direction(cities):
    min_x = min(map(lambda x: x[0], cities))
    min_y = min(map(lambda x: x[1], cities))
    max_x = max(map(lambda x: x[0], cities))
    max_y = max(map(lambda x: x[1], cities))
    return max_x - min_x > max_y - min_y


def divide_half(cities):
    n = len(cities) / 2
    left = cities[0:(n+1)]
    right = cities[n:]
    return cities[n], left, right


def divide(cities):
    if len(cities) <= 3:
        return cities
    else:
        if divide_direction(cities):
            sorted_cities = sorted(cities, key = lambda x: x[0])
        else:
            sorted_cities = sorted(cities, key =  lambda x: x[1])

        common, left, right = divide_half(sorted_cities)
        cities_first = divide(left)
        cities_second = divide(right)
        return merge(cities_first, cities_second, common)
    
    
def opt_2(size, path, distance_table):
    total = 0
    while True:
        count = 0
        for i in xrange(size - 2):
            i1 = i + 1
            for j in xrange(i + 2, size):
                if j == size - 1:
                    j1 = 0
                else:
                    j1 = j + 1
                if i != 0 or j1 != 0:
                    l1 = distance_table[path[i]][path[i1]]
                    l2 = distance_table[path[j]][path[j1]]
                    l3 = distance_table[path[i]][path[j]]
                    l4 = distance_table[path[i1]][path[j1]]
                    if l1 + l2 > l3 + l4:
                        new_path = path[i1:j+1]
                        path[i1:j+1] = new_path[::-1]
                        count += 1
        total += count
        if count == 0: break
    return path
    
def solve_divide(cities):
    N = len(cities)
    distances = [[0] * N for i in range(N)]
    for i in range(N):
        for j in range(N):
            if i != j:
                distances[i][j] = distances[j][i] = distance(cities[i], cities[j])
    solution = divide(cities)
    solution_index = make_path_index(cities, solution)
    opt_solution = opt_2(len(cities), solution_index, distances)
    return opt_solution




if __name__ == '__main__':
    assert len(sys.argv) > 1
    solution = solve_divide(read_input(sys.argv[1]))
    print_solution(solution)
    overwrite_solution(solution, sys.argv[2])
