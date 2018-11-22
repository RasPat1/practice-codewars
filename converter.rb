def mixed_fraction(s)
  Converter.new(s).convert
end

# Converts an unreduced simple fraction into a valid reduced mixed fraction
class Converter
  attr_accessor :input

  def initialize(input)
    @input = input
  end

  def convert
    result = 0

    numerator, denominator = input.split('/').map(&:to_i)

    if denominator == 0
      raise ZeroDivisionError.new
    elsif numerator == 0
      return '0'
    end

    sign = find_sign(numerator, denominator)

    # convert to absolute value since we've moved the sign to the 
    # outermost part of our reduced fraction and modulo is off 
    # by 1 when negative
    numerator = numerator.abs
    denominator = denominator.abs

    # Find out whole number of times the denominator fits into the numerator
    # Ruby truncates when dividing so this is just straight division
    integer = numerator / denominator

    # Find fractional remainder
    numerator = numerator % denominator
    denominator = denominator

    # Reduce fractional remainder
    numerator, denominator = reduce_fraction(numerator, denominator)

    # Build result
    build_result(sign, integer, numerator, denominator)
  end

  def find_sign(numerator, denominator)
    sign_count = 0

    if numerator < 0
      sign_count += 1
    end
    if denominator < 0
      sign_count += 1
    end

    sign_count == 1 ? '-' : ''
  end

  # Recursively reduce the fraction using all possible factors
  # between 2 and the smaller term
  def reduce_fraction(numerator, denominator)
    smaller_term = numerator < denominator ? numerator : denominator

    smaller_term.downto(2) do |factor|
      if numerator % factor == 0 && denominator % factor == 0
        numerator /= factor
        denominator /= factor
        result = reduce_fraction(numerator, denominator)
      end
    end

    [numerator, denominator]
  end

  # Create the string representation of this fraction as asked by the question
  # No integer displayed when integer part is 0
  # No fractin displayed when numerator part is 0
  # No trailing or leading psaces
  # No spaces between the sign and the following number (integer or numerator)
  # There is no such thing as negative 0
  def build_result(sign, integer, numerator, denominator)
    result = ''

    if sign == '-'
      result += sign
    end

    # Don't show negative in front of zeroes
    # Leave out zero when there is only a fractional part
    if integer == 0 && numerator == 0
      return 0
    elsif integer == 0 && numerator != 0
    else
      result += integer.to_s
    end

    if integer != 0 && numerator != 0
      result += ' '
    end

    # Don't show fractional section when there is no fraction
    if numerator != 0
      result += "#{numerator}/#{denominator}"
    end

    result
  end
end
