/*
 * co2.c
 *
 * Created: 02.05.2021 08:16:31
 *  Author: popad
 */

#include "FreeRTOS.h"
#include <stdio.h>
#include "stdio_driver.h"
#include "co2.h"
#include "hardware.h"
#include "semphr.h"
#include "mh_z19.h"
#include "task.h"

inline void co2_init() {
	mh_z19_initialise(ser_USART3);
	mh_z19_injectCallBack(co2Callback);
}

inline void co2_run(void) {
	vTaskDelay(50);
	int statusCode = mh_z19_takeMeassuring();
	if (statusCode != MHZ19_OK) {
		printf("CO2 measuring error %d\n", statusCode);
	}
	vTaskDelay(9900);
}

void co2Task(void* pvParameters) {
	(void)pvParameters;

	co2_init();

	while (1) {
		co2_run();
	}
}

inline void co2Callback(uint16_t ppm) {
	xSemaphoreTake(hardware_semaphore, portMAX_DELAY);
	entry_data.co2 = ppm;
	xSemaphoreGive(hardware_semaphore);
}
