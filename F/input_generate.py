from random import randint

def random_big_number():
  return str.join('', [str(randint(0, 9)) for i in range(randint(100, 150))])


t = 20
print(t)
for i in range(t):
  print(random_big_number())
