# ruby generate_many.rb >in_many.txt

n = 4000
c = 100000

points = []
n.times do |i|
  points << "#{i} #{i.even? ? i : c-i}"
end

puts "#{n}\n#{points.join("\n")}"

