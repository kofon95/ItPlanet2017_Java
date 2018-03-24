@sieve = Array.new(100000, false)

def add(n)
  while @sieve[n]
    n += 1
  end
  @sieve[n] = true
  n
end

def rem(n)
  @sieve[n] = false
end

(0...gets.to_i).each do
  t, n = gets.split(' ').map &:to_i
  if t == 1
    puts add n
  else
    rem n
  end
end



