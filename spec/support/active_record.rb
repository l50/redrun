require 'active_record'
require 'yaml'
require 'database_cleaner'

DATABASE_ENV   = 'redruntest'
MIGRATIONS_DIR = ENV['MIGRATIONS_DIR'] || '../db/migrate/'
@config        = YAML.load_file('../config/database.yml')[DATABASE_ENV]

ActiveRecord::Base.establish_connection @config
ActiveRecord::Migrator.migrate MIGRATIONS_DIR, ENV['VERSION'] ? ENV['VERSION'].to_i : nil

RSpec.configure do |config|

  config.before :suite do
    DatabaseCleaner.strategy = :transaction
    DatabaseCleaner.clean_with :truncation
  end
  config.before :each do
    DatabaseCleaner.start
  end
  config.after :each do
    DatabaseCleaner.clean
  end
end