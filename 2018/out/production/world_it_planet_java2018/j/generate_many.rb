# ruby generate_many.rb >in_many.txt

n = 4000
c = 100000

points = []
n.times do |i|
  points << "#{rand(0..c)} #{rand(0..c)}"
end

puts "#{n}\n#{points.join("\n")}"

