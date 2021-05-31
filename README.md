# SEP4_IOT

Instalation guide: 

Clone the project, the final version of the hardware is in the hardware2 branch.

The freeRTOS and drivers file need to be cloned separately from 
https://github.com/ihavn/VIA_FreeRTOS_AVRMEGA/tree/a9b647b3072a0c2603b06a4e402b58de5b2cd666 
https://github.com/ihavn/IoT_Semester_project.git


In "LoRaWANHAndler.c" set the LORA_appEUI and LORA_appKEY to your keys provided by the admin

"FreeRTOS/src/FreeRTOSConfig.h" -- change the value of configTOTAL_HEAP_SIZE from 2500 to 3000

