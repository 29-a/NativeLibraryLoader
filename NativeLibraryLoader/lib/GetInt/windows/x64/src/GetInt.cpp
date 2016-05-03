#include "de_janik_Main.h"

extern "C" int GetInt();

JNIEXPORT jint JNICALL Java_de_janik_Main_GetInt(JNIEnv* env, jclass jclass)
{
	return GetInt();
}
