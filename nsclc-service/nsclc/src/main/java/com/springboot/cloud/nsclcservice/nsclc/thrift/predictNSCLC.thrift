namespace java thrift.generated
namespace py py.thrift.generated

typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

struct PredictInfo {
    1:optional String featurePath,
    2:optional int predictResult
}

exception DataException {
    1:optional String message,
    2:optional String callback,
    3:optional String date
}

service PredictService {
    PredictInfo predictNSCLC(1:required String image_path,2:required String label_path,3:required String modelLoc) throws (1:DataException dataException)
}