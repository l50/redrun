#!usr/bin/env ruby
require 'active_record'
require 'yaml'
require 'logger'

# Rakefile to create and drop tables from the database with ease
#
# Instructions for running rake migrations:
# rake db:migrate <-- Migrate the database tables
# rake db:migrate VERSION=0 <-- Drop the database tables
#

namespace :db do
  task :environment do
    DATABASE_ENV = 'redrun'
    MIGRATIONS_DIR = ENV['MIGRATIONS_DIR'] || 'db/migrate/'
  end

  task :configuration => :environment do
    @config = YAML.load_file('config/database.yml')[DATABASE_ENV]
  end

  task :configure_connection => :configuration do
    ActiveRecord::Base.establish_connection @config
    ActiveRecord::Base.logger = Logger.new(File.open('src/redrun/database/logs/database.log', 'a'))
  end

  desc 'Migrate the database (options: VERSION=x, VERBOSE=false).'
  task :migrate => :configure_connection do
    ActiveRecord::Migration.verbose = true
    ActiveRecord::Migrator.migrate MIGRATIONS_DIR, ENV['VERSION'] ? ENV['VERSION'].to_i : nil
  end

  desc 'Rolls the schema back to the previous version (specify steps w/ STEP=n).'
  task :rollback => :configure_connection do
    step = ENV['STEP'] ? ENV['STEP'].to_i : 1
    ActiveRecord::Migrator.rollback MIGRATIONS_DIR, step
  end
end