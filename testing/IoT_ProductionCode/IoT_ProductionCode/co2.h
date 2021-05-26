#pragma once
/*
 * co2.h
 *
 * Created: 02.05.2021 08:16:48
 *  Author: popad
 */

#include <stdint.h>

void co2_init();
void co2_run(void);
void co2Callback(uint16_t ppm);

