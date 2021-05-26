#include "gtest/gtest.h"
#include "C:\FOURTH_SEMESTER\ESW\fff-master\fff.h"
DEFINE_FFF_GLOBALS

extern "C" {
#include <stdio.h>

#include "FreeRTOS.h"
#include "semphr.h"
#include "message_buffer.h"
#include "mh_z19.h"
#include "serial.h"
#include "stdio_driver.h"
#include "task.h"

#include <hardware.h>
#include <co2.h>
}

FAKE_VOID_FUNC(vTaskDelay, TickType_t);
FAKE_VALUE_FUNC(BaseType_t, xSemaphoreGive, SemaphoreHandle_t);
FAKE_VALUE_FUNC(BaseType_t, xSemaphoreTake, SemaphoreHandle_t, TickType_t);

FAKE_VOID_FUNC(mh_z19_initialise, serial_comPort_t);
FAKE_VOID_FUNC(mh_z19_injectCallBack, void*);
FAKE_VALUE_FUNC(mh_z19_returnCode_t, mh_z19_takeMeassuring);


class TaskTest : public ::testing::Test {
protected:
	void SetUp() override {
		RESET_FAKE(vTaskDelay);
		RESET_FAKE(xSemaphoreGive);
		RESET_FAKE(xSemaphoreTake);
		RESET_FAKE(mh_z19_initialise);
		RESET_FAKE(mh_z19_injectCallBack);
		RESET_FAKE(mh_z19_takeMeassuring);
		FFF_RESET_HISTORY();
	}

	void TearDown() override{}
};

//Testing if initialization calls the appropiate functions with 
//the wanted values
TEST_F(TaskTest, initialisationCalls) {
	co2_init();
	EXPECT_EQ(1, mh_z19_initialise_fake.call_count);
	EXPECT_EQ(ser_USART3, mh_z19_initialise_fake.arg0_val);
	EXPECT_EQ(1, mh_z19_injectCallBack_fake.call_count);
}

//Testing if the run methods calls the appropiate functions, and the values history
TEST_F(TaskTest, runTaskCalls) {
	co2_run();

	EXPECT_EQ(2, vTaskDelay_fake.call_count);

	//start delay
	EXPECT_EQ(50, vTaskDelay_fake.arg0_history[0]);
	//end delay
	EXPECT_EQ(9900, vTaskDelay_fake.arg0_history[1]);

	EXPECT_EQ(1, mh_z19_takeMeassuring_fake.call_count);
	//return value should be the default code, OK
	EXPECT_EQ(MHZ19_OK, mh_z19_takeMeassuring_fake.return_val);
}

//Testing a mockup call of the co2 callback funtion
TEST_F(TaskTest, callbackMockCall) {
	co2Callback(123);

	//taking and giving semaphore
	EXPECT_EQ(1, xSemaphoreTake_fake.call_count);
	EXPECT_EQ(1, xSemaphoreGive_fake.call_count);

	//using right arguments
	EXPECT_EQ(hardware_semaphore, xSemaphoreTake_fake.arg0_val);
	EXPECT_EQ(portMAX_DELAY, xSemaphoreTake_fake.arg1_val);
	EXPECT_EQ(hardware_semaphore, xSemaphoreGive_fake.arg0_val);

	//measured value being stored
	EXPECT_EQ(123, entry_data.co2);

}