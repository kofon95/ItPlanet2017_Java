from random import randint

n = 5000
p = 100#n-1
cities = [None]*n
roads = [None]*n
weights = [None]*n


def random_array(n, size, r):
  res = [None]*size
  for i in range(size):
    res[i] = randint(1, r)
  return res


for i in range(n):
  cities[i] = i+1
  size = randint(0, p)
  roads[i] = random_array(n, size, n)
  weights[i] = random_array(n, size, 40)

ways = [None]*randint(0, 100)
for i in range(len(ways)):
  ways[i] = randint(1, n), randint(1, n)


print(1)
print(len(cities))
for i in range(len(cities)):
  print(cities[i])
  print(len(roads[i]))
  for rw in range(len(roads[i])):
    print(roads[i][rw], weights[i][rw])

print(len(ways))
for i in ways:
  print(i[0], i[1])
