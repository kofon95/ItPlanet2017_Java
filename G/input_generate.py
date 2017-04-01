from random import randint

n = randint(2, 4)*2
m = 1000000

print(n)
print('('*n + ')'*n)
print(m)

for i in range(m):
  if randint(0, 2) != 0:
    k = randint(1, n)
  else:
    k = 0
  print(k)
