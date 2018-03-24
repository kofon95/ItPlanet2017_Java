# ruby generate_sq.rb >in_sq.txt

n = 4000
c = 100000

points = []
(n/4).times do |i|
  points << "#{i} #{i}"
  points << "#{c-i} #{i}"
  points << "#{i} #{c-i}"
  points << "#{c-i} #{c-i}"
end

puts "#{n}\n#{points.join("\n")}"
