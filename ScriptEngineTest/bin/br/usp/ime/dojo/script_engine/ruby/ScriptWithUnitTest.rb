require "test/unit"

class SimpleNumber
 
  def initialize( num )
    raise unless num.is_a?(Numeric)
    @x = num
  end
 
  def add( y )
    @x + y
  end
 
  def multiply( y )
    @x * y
  end
 
end


class TestSimpleNumber < Test::Unit::TestCase
 
  def test_simple
     assert_equal(4, SimpleNumber.new(2).add(2) )
     assert_equal(6, SimpleNumber.new(2).multiply(3) )
   end
 
 end
