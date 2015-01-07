require 'active_record'

# Migration to create the Characters tables
class CreateMapobjects < ActiveRecord::Migration

  def change
    create_table :mapobjects do |t|
      t.column :object_name, :string, null: false
      t.column :location, :string, null: false
      t.column :ground_texture, :string, null: false
      t.column :wall_texture, :string, null: false
      t.column :direction, :string, null: false
      t.column :trap_type, :string
      t.belongs_to :map, null: false
    end
    add_index :mapobjects, [:object_name], :name => "index_mapobjects_on_object_name"
    add_index :mapobjects, [:location], :name => "index_mapobjects_on_location"
    add_index :mapobjects, [:ground_texture], :name => "index_mapobjects_on_ground_texture"
    add_index :mapobjects, [:wall_texture], :name => "index_mapobjects_on_wall_texture"
    add_index :mapobjects, [:direction], :name => "index_mapobjects_on_direction"
    add_index :mapobjects, [:trap_type], :name => "index_mapobjects_on_trap_type"
    add_index :mapobjects, [:map_id], :name => "index_mapobjects_on_map_id"
  end
end