require './converter.rb'
class ConverterSpec
  attr_accessor :tests

  def initialize(tests)
    @tests = tests
  end

  def run
    pass_count = 0
    fail_count = 0

    tests.each do |test|
      input = test[0]
      output = test[1]

      result = Converter.new(input).convert
      if result != output
        puts "Failed: Expected #{input} to convert to #{output} but instead was #{result.to_s}"
        fail_count += 1
      else
        puts "Passed: #{input} => #{output}"
        pass_count += 1
      end
    end
  end
end


tests = [
  ['42/9', '4 2/3'],
  ['6/3', '2'],
  ['4/6', '2/3'],
  ['4/-6', '-2/3'],
  ['0/18891', '0'],
  ['-10/7', '-1 3/7'],
  ['10/-7', '-1 3/7'],
  ['-22/-7', '3 1/7'],
]

ConverterSpec.new(tests).run

# Test.expect_error("Must raise ZeroDivisionError") do mixed_fraction("0/0") end
# Test.expect_error("Must raise ZeroDivisionError") do mixed_fraction("3/0") end