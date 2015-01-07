source 'https://rubygems.org'

# Needed for debugging
gem 'ruby-debug'

# Needed for multithreading
gem 'peach'

# Needed for creating log files
gem 'logger'

group :db do
  # Needed for ORM functionality
  gem 'activerecord', '= 4.1.4'
  # Needed for interfacing with database
  gem 'jdbc-sqlite3'
  # Sqlite3 JDBC adapter
  gem 'activerecord-jdbcsqlite3-adapter'
end

group :test do
  # Needed for ruby specific test cases
  gem 'rspec-rails'
  # Needed for should syntax in rspec test cases
  gem 'minitest'
  # Needed for should syntax in rspec test cases
  gem 'shoulda-matchers', '= 2.5.0'
  # Sanitize db test data automatically
  gem "database_cleaner"
end