from random import randint

t = 10
n = 100000

print(t)
print()
while t > 0:
  t -= 1
  print(n)
  for i in range(n):
    print(randint(-10000, 10000), randint(-10000, 10000))
  print()
