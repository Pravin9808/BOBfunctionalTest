From Ubuntu
WorkDir /app
--identification where source code should be save

COPY requirement.txt/app
COPY devos /app
---copy all file where all application dependencies exist in working directry.
--with source code and dependency you will form bindary of ypur application.

---now you have ubuntu base image, so yu need to uinstall ython on top of it.

RUN apt-get udate && \
    apt-get install -y python3 python-3-pi && \
    pip install -r requirement.txt && \
    cd Devops

--entrypoint and cmd can be used to execute as startcommand . when docker run command both entrypoint and cmd command runas start command.only diff
is entrypoint you can't change.
As auser when you run your applciation .entrypoint can't be change in image but cmd that is configurable. Like i told you i can also in cmd also.
I wnat pepole to change executable point in entrypoint. but oin cmd command we can change and provide access to change port or runserver.
--github basically sed for storing surce
ENTRYPOINT ["Python3"]
CMD ["runserver","0.0.0.0/8000"]
    
