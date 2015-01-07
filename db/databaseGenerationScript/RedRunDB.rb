#!usr/bin/env ruby
require 'sqlite3'

#
# This class generates the tables for the Red Run Database.
#
# Author:: Jayson Grace (mailto:jaysong@unm.com)
# Version:: 1.0
# Since:: 2014-08-11
#
class RedRunDB

  def initialize
    @db = SQLite3::Database.new('../redRun.db')
    @db.execute("PRAGMA foreign_keys = ON;")
  end

  # Delete all tables and their associated data.
  def delete_tables
    delete_characters
    delete_kiosks
    delete_traps
    delete_buttons
   end

  # Generate all of the tables
  def generate_tables
    create_characters
    create_kiosks
    create_traps
    create_buttons
  end

  private

  # Create the Characters table
  def create_characters
    @db.execute %Q{
    CREATE TABLE Characters (
    id INTEGER PRIMARY KEY NOT NULL,
    character_name TEXT NOT NULL,
    image TEXT NOT NULL,
    team TEXT NOT NULL
    );
  }
  end

  # Delete the Characters table
  def delete_characters
    @db.execute("DROP TABLE Characters")
  end

  # Create Kiosks table
  def create_kiosks
    @db.execute %Q{
  CREATE TABLE Kiosks (
  id INTEGER PRIMARY KEY NOT NULL,
  location TEXT NOT NULL,
  cooldown NUMERIC NOT NULL,
  Traps_id INTEGER NOT NULL,
  Buttons_id INTEGER NOT NULL,
  FOREIGN KEY(Traps_id) REFERENCES Traps(id),
  FOREIGN KEY(Buttons_id) REFERENCES Buttons(id)
  );
    }
  end

  # Delete the Kiosks table
  def delete_kiosks
    @db.execute("DROP TABLE Kiosks")
  end

# Create Buttons table
  def create_buttons
    @db.execute %Q{
      CREATE TABLE Buttons (
        id INTEGER PRIMARY KEY NOT NULL,
        state TEXT NOT NULL,
        Traps_id INTEGER NOT NULL,
        FOREIGN KEY (Traps_id) REFERENCES Traps (id)
      );
    }
  end

  # Delete the Buttons table
  def delete_buttons
    @db.execute("DROP TABLE Buttons")
  end

  # Create Traps table
  def create_traps
    @db.execute %Q{
  CREATE TABLE Traps (
  id INTEGER PRIMARY KEY NOT NULL,
  type TEXT NOT NULL,
  Kiosks_id INTEGER NOT NULL,
  Buttons_id INTEGER NOT NULL,
  FOREIGN KEY (Kiosks_id) REFERENCES Kiosks (id),
  FOREIGN KEY (Buttons_id) REFERENCES Buttons (id)
    );
  }
  end

  # Delete the Traps table
  def delete_traps
    @db.execute("DROP TABLE Traps")
  end
end

# Main - needs to be turned into a test spec file
run = RedRunDB.new
run.generate_tables



