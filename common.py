def read_input(filename):
    with open(filename) as f:
        cities = []
        for line in f.readlines()[1:]:  # Ignore the first line.
            xy = line.split(',')
            cities.append((float(xy[0]), float(xy[1])))
        return cities


def format_solution(solution):
    return 'index\n' + '\n'.join(map(str, solution))


def print_solution(solution):
    print(format_solution(solution))


def overwrite_solution(solution, filename):
    with open(filename, 'w') as f:
        f.writelines("index\n")
        for i in range(len(solution)):
            f.writelines(str(solution[i]) + '\n')
