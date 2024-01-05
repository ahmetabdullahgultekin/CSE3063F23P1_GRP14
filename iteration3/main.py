import logging
import os
from datetime import datetime

import CourseRegistrationSystem

# Define the format
log_format = '%(asctime)s - %(levelname)s - %(name)s.%(funcName)s - %(message)s'

# Define the filename with a timestamp
filename = f'{datetime.now().strftime("%Y_%m_%d_%H_%M_%S")}.log'

# Create a logs directory if it doesn't exist
if not os.path.exists('logs'):
    os.makedirs('logs')

# Set the logging configuration
logging.basicConfig(filename=os.path.join('logs', filename), filemode='w', format=log_format, level=logging.INFO)

os.environ["LOG_CFG"] = "log4j2.xml"  # to set the log4j2 configuration file
courseRegistrationSystem = CourseRegistrationSystem.CourseRegistrationSystem()
courseRegistrationSystem.start()
