# DROP the SCHEMA
drop database miraen;
# CREATE the SCHEMA + USE specific SCHEMA
create database miraen;
use miraen;

select * from EvalConceptV4;

# START INNODB SETTING
# Check value
# Modify value
set global innodb_flush_log_at_trx_commit=0;
set global innodb_buffer_pool_size = 4G;
set global innodb_log_buffer_size = 256M;
set global innodb_log_file_size = 1G;
set global innodb_write_io_threads = 16;
#############################################################
show variables like 'innodb_flush_log_at_trx_commit';
show variables like 'innodb_buffer_pool_size';
show variables like 'innodb_log_buffer_size';
show variables like 'innodb_log_file_size';
show variables like 'innodb_write_io_threads';
# START PROPERTIES SETTING
# Check value
# Modify value
show variables like 'sort_buffer_size';
set global sort_buffer_size=1000000;
# Default
# set global sort_buffer_size=262144;
show variables like 'myisam_sort_buffer_size';
set global myisam_sort_buffer_size=1000000;
# Default
# set global myisam_sort_buffer_size=426770432;
show variables like 'myisam_max_sort_file_size';
set global myisam_max_sort_file_size=10000000;
# Default
# set global myisam_max_sort_file_size=107374182400;
show variables like 'max_allowed_packet';
set global max_allowed_packet=1000000;
# Default
# set global max_allowed_packet=67108864;
show variables like 'bulk_insert_buffer_size';
set global bulk_insert_buffer_size=26214400;
# Default
# set global bulk_insert_buffer_size=8388608;
show variables like 'key_buffer_size';
set global key_buffer_size=500000;
# Default
# set global key_buffer_size=8388608;
# 설정을 적용한 뒤에 재시작을 꼭 해주세요!
restart;