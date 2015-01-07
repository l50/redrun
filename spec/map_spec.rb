require_relative 'support/active_record.rb'
require 'minitest'
require 'shoulda-matchers'

class Map < ActiveRecord::Base
  has_many :character, :class_name => "Character"
  has_many :kiosk, :class_name => "Kiosk"
  has_many :trap, :class_name => "Trap"
  has_many :button, :class_name => "Button"
  has_many :mapobject, :class_name => "Mapobject"
  I18n.enforce_available_locales = true
end

describe Map do

  # Create test case -- runs before all it statements
  before(:all) do
    @attr = {map_name: 'iceworld', sky_box: 'iceflats', floor: 'snow'}
    Map.create(@attr)
    p Map.all
  end

  it { should have_many :character }
  it { should have_many :kiosk }
  it { should have_many :trap }
  it { should have_many :button }
  it { should have_many :mapobject }
end