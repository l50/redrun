require_relative 'support/active_record.rb'
require 'minitest'
require 'shoulda-matchers'

class Mapobject < ActiveRecord::Base
  belongs_to :map, :class_name => 'Map', :foreign_key => 'map_id'
  validates_presence_of :object_name
  validates_presence_of :location
  validates_presence_of :texture
  validates_presence_of :direction
  I18n.enforce_available_locales = true
end

describe Mapobject do

  # Create test case -- runs before all it statements
  before(:all) do
    @attr = {object_name: 'corner', location: '12,50,77',
        texture: 'corner.png', direction: 'NORTH', map_id: 1}
    Mapobject.create(@attr)
    p Mapobject.all
  end

  it { should validate_presence_of :object_name }
  it { should validate_presence_of :location }
  it { should validate_presence_of :texture }
  it { should validate_presence_of :direction }
end