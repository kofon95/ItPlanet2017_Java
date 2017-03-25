var factors = [4, 6, 9, 10, 14, 15, 21, 22, 25, 26, 33, 34, 35, 38, 39, 46, 49, 51, 55, 57, 58, 62, 65, 69, 74, 77, 82, 85, 86, 87, 91, 93, 94, 95, 106, 111, 115, 118, 119, 121, 122, 123, 129, 133, 134, 141, 142, 143, 145, 146, 155, 158, 159, 161, 166, 169, 177, 178, 183, 185, 187,
   194, 201, 202, 203, 205, 206, 209, 213, 214, 215, 217, 218, 219, 221, 226, 235, 237, 247]
factors = factors.filter(function(x){ return !Number.isInteger(Math.sqrt(x)); })

var a = [0];
var other = [];
for (let i = 1; i <= 1000000; i++){
  let sq = Math.sqrt(i)
  let s = 0;
  j2 = Math.floor(sq);
  for (let j = 1; j <= j2; j++){
    if (i % j == 0) s++;
  }
  let t = Number.isInteger(sq) ? 1 : 0;
  s = s*2 - t;
  a.push(s);
  if (s > 247) other.push(i);
}
var res = [];
var factorsSet = new Set(factors);
for (let i = 0; i < a.length; i++){
  if (factorsSet.has(a[i])) res.push(i);
//   if (factors.indexOf(a[i]) >= 0) res.push(i + " " + a[i]);
}
for (let i = 1; i*9-1 < res.length; i++){
  console.log(res[i*9-1]);
}