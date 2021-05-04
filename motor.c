/*
 * motor.c
 *
 * Created: 04.05.2021 13:51:49
 *  Author: popad
 */ 

#include <ATMEGA_FreeRTOS.h>
#include <stdio.h>
#include <stdio_driver.h>
#include "hardware.h"
#include "motor.h"
#include "semphr.h"

void lightMotorTask(void* pvParameters){
	(void) pvParameters;
	
	while(1){
		vTaskDelay(150);
		
		puts("motor task going");
	}
}

