cmake_minimum_required(VERSION 3.4.1)
add_library(
             encrypt_key
             SHARED
             src/main/cpp/EncryptKey.cpp )

target_link_libraries(
                       encrypt_key
                       ${log-lib} )
