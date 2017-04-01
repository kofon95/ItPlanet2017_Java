from random import randint, shuffle

t = 500
print(t)

def generate(a, b):
  n = randint(a, b)
  s = chr(randint(97, 97+25))
  res = []
  for i in range(n):
    s += chr(randint(97, 97+25))
    res.append(s)
    s = s[1]
  shuffle(res)
  
  print(n)
  for s in res:
    print(s)


for i in range(t-100):
  generate(10**4, 10**5)
for i in range(100):
  generate(1, 5)
