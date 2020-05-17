require 'byebug'

# Return an array of arrays where each iner array contains
# cities who are anagrams of each otehr
class CitySorter
  attr_reader :cities

  def initialize
    @cities = {}
  end

  def sort(city_list)
    # Strategy
    # Create a hash where the keys are the name of a city
    # with its letters sorted and its values are an array
    # of cities from the city list that are equal to the
    # key when sorted

    city_list.each do |city|
      add_city(city)
    end

    format_output
  end

  private
  # Return a string with the letters of the initial string sorted
  def sort_letters(string)
    string.downcase.split('').sort.join('')
  end

  def add_city(city)
    sorted_city = sort_letters(city)

    if cities.key?(sorted_city)
      cities[sorted_city] << city
    else
      cities[sorted_city] = [city]
    end
  end

  def format_output
    result = []

    cities.each do |key, value|
      result << value
    end

    result.to_s
  end
end

class CitySorterTest
  attr_reader :city_sorter

  def initialize
    @city_sorter = CitySorter.new
  end

  def call(city_list)
    city_sorter.sort(city_list)
  end
end

test_case = ['Tokyo', 'London', 'Rome', 'Donlon', 'Kyoto', 'Paris', 'Ykoto']
puts CitySorterTest.new.call(test_case)