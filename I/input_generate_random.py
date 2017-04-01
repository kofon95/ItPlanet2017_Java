from random import randint, shuffle

t = 500
print(t)

def generate(a, b):
  n = randint(a, b)
  print(n)
  for i in range(n):
    print(chr(randint(97, 97+25)) + chr(randint(97, 97+25)))


for i in range(t-100):
  generate(10**4, 10**5)
for i in range(100):
  generate(1, 5)
