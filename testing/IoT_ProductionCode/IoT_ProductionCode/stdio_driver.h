#pragma once

#include <stdbool.h>
#include <stdint.h>

void stdio_initialise(uint8_t usartNo);
bool stdio_inputIsWaiting(void);