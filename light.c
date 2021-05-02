/*
 * light.c
 *
 * Created: 02.05.2021 08:24:13
 *  Author: natmj
 */ 

#include <ATMEGA_FreeRTOS.h>
#include <stdio.h>
#include <stdio_driver.h>
#include "hardware.h"
#include "light.h"
#include "tsl2591.h"
#include "semphr.h"

void lightTask(void* pvParameters) {
	(void)pvParameters;

	while (1) {
		vTaskDelay(pdMS_TO_TICKS(7000UL));
		int statusCode = tsl2591_fetchData();
		if (statusCode != TSL2591_OK) {
			printf("Light data error: %d\n", statusCode);
		}
	}

}


void lightCallback(tsl2591_returnCode_t rc) {
	float lux;
	xSemaphoreTake(hardware_semaphore, portMAX_DELAY);
	
	if (TSL2591_OK == (rc = tsl2591_getLux(&lux)))
	{
		printf("Lux: %d\n", (uint16_t)lux);
		entry_data.light = (uint16_t)lux;
	}
	else if (TSL2591_OVERFLOW == rc)
	{
		printf("Lux overflow\n");
	}
	xSemaphoreGive(hardware_semaphore);
}