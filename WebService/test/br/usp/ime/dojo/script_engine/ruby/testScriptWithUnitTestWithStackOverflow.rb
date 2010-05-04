require "test/unit"

class SimpleNumber

	def initialize( num )
		raise unless num.is_a?(Numeric)
		@x = num
	end

	def factorial
		if @x > 0
			@x * SimpleNumber.new(@x-1).factorial
		else
			1
		end
		puts 5
	end

end

class TestSimpleNumber < Test::Unit::TestCase

	def test_simple
		assert_equal(120, SimpleNumber.new(5).factorial)
	end

	def test_fail
		assert_equal(120, SimpleNumber.new(9999).factorial)
	end
end
