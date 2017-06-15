// MPU-6050 Accelerometer + Gyro
// -----------------------------

#ifndef MPU6050Support_h
#define MPU6050Support_h

typedef struct accel_gyro_temp_t
{
  int16_t accel_x;
  int16_t accel_y;
  int16_t accel_z;
  int16_t gyro_x;
  int16_t gyro_y;
  int16_t gyro_z;
  int16_t temp;
};

typedef struct offset_calibration
{
  int32_t accel_x;
  int32_t accel_y;
  int32_t accel_z;
  int32_t gyro_x;
  int32_t gyro_y;
  int32_t gyro_z;
  int32_t temp;
};

typedef struct accel_temp_gyro_raw_t
{
  uint8_t accel_xh;
  uint8_t accel_xl;
  uint8_t accel_yh;
  uint8_t accel_yl;
  uint8_t accel_zh;
  uint8_t accel_zl;
  uint8_t temp_h;
  uint8_t temp_l;
  uint8_t gyro_xh;
  uint8_t gyro_xl;
  uint8_t gyro_yh;
  uint8_t gyro_yl;
  uint8_t gyro_zh;
  uint8_t gyro_zl;
};

int setup_MPU6050();
int read_raw_MPU6050(accel_temp_gyro_raw_t* data_ptr);
int convert_raw_MPU6050(accel_gyro_temp_t* dest_ptr, accel_temp_gyro_raw_t* raw_ptr);
int low_pass_filter_MPU6050(accel_gyro_temp_t* lowpass_ptr, accel_gyro_temp_t* data_ptr);

#endif
