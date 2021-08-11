CUR_PATH=$(cd `dirname $0`;cd ..;pwd)

VERSION=1.0.0
TARGET_JAR="$CUR_PATH/target/MigrationAssessment-${VERSION}-SNAPSHOT.jar"
CLASS_PATH=$CUR_PATH"/target/classes"
suspend='n'
while [ $# -gt 0 ]; do
    case "$1" in
        --debug)
            if [ "$2"X = X ]; then
                echo "no given correct version information, such as: debug/release/memcheck"
                exit 1
            fi
            debug_port=$2
            shift 2
            ;;
        --suspend)
            suspend='y'
            shift 1
            ;;
    esac
done


for i in `ls $CUR_PATH/target/lib`; do
  CLASS_PATH="$CLASS_PATH:$CUR_PATH/target/lib/$i"
done
chmod +x $CUR_PATH/bin/* -R 2>/dev/null
# todo java_options
if [ ! "$debug_port"X == X ]; then
  JAVA_DEBUG="-agentlib:jdwp=transport=dt_socket,server=y,suspend=$suspend,address=$debug_port"
fi
cd $CUR_PATH && java -classpath $CLASS_PATH $JAVA_DEBUG com.huawei.payroll.ReactAndSpringDataRestApplication
