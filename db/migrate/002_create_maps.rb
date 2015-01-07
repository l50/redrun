require 'active_record'

# Migration to create the Buttons tables
class CreateMaps < ActiveRecord::Migration

  def change
    create_table :maps do |t|
      t.column :map_name, :string, null: false, unique: true
      t.column :sky_box, :string, null: false, unique: true
      t.column :floor, :string, null: false, unique: true
      t.column :light_position, :string, null: false
    end
    add_index :maps, [:map_name], :name => "index_maps_on_map_name"
    add_index :maps, [:sky_box], :name => "index_maps_on_sky_box"
    add_index :maps, [:floor], :name => "index_maps_on_floor"
    add_index :maps, [:light_position], :name => "index_maps_on_light_position"
  end
end