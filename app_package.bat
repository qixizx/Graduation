
cd ../tpsoft-boot/
call mvn install -DskipTests
cd ../tpsoft-boot-base-common/
call mvn install -DskipTests
cd ../tpsoft-boot-module-system/
call mvn install -DskipTests
cd ../aquarius/
call mvn install