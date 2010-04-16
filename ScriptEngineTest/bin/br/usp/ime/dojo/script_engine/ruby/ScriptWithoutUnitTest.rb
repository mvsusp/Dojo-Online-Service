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

end
