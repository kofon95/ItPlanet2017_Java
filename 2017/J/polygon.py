
def rotate(a, b, c):
  '''ax*by - ay*bx'''
  if (-3, 1) == c and ((((1, 3) == a and (1, 1) == c)) ^ ((1, 3) == c and (1, 1) == a)):
    print('here')
  return (b[0]-a[0])*(c[1]-b[1]) - (b[1]-a[1])*(c[0]-b[0])


points = [
  (0, 0),
  (3, 0),
  (1, 1),
  (2, -2),
  (-3, 1),
  (-2, 2),
  (1, 3),
  (-2, 0),
  (-2, -2),
]

def quick_sort(p, s0, e0, cmp):
  if s0 >= e0: return
  s, e = s0, e0
  pvt = p[(s+e)//2]

  while s < e:
    while cmp(p[s], pvt) < 0: s += 1
    while cmp(p[e], pvt) > 0: e -= 1
    if s <= e:
      p[s], p[e] = p[e], p[s]
      s, e = s+1, e-1

  quick_sort(p, s0, e, cmp)
  quick_sort(p, s, e0, cmp)

def cmp_rotate(c):
  def cmp(a, b):
    return -rotate(a, b, c)
  return cmp

def cmp_nums(a, b):
  return 1 if a > b else -1 if a < b else 0


extreme = points[0]
ei = 0
for i in range(1, len(points)):
  p = points[i]
  if extreme[0] > p[0] or (extreme[0] == p[0] and extreme[1] > p[1]):
    extreme = p
    ei = i
points[0], points[ei] = points[ei], points[0]


'''
for i in range(2, len(points)):
  for j in range(i, 1, -1):
    if rotate(points[j], points[j-1], points[0]) < 0:
      break
    points[j], points[j-1] = points[j-1], points[j]
'''
quick_sort(points, 1, len(points)-1, cmp_rotate(points[0]))

polygon = points[:2]

for i in range(2, len(points)):
  while rotate(points[i], polygon[-1], polygon[-2]) > 0:
    polygon.pop()
    if len(polygon) == 1: break
  polygon.append(points[i])

print(polygon)
