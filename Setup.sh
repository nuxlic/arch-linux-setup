#!/bin/sh
echo $'\n'"Bienvenido al Instalador de arch-linux"
echo "--------------------------------------"
echo "Elija una opcion:"
echo "   1.Instalar sistema base"
echo "   2.Post-Instalacion"
read resp
echo "Se conecta a internet por wifi? y/n"
read red
iwconfig
echo "Ingrese el nombre de su interfaz de red:"
read interface
if [ $red = "y" ]
then
	ip link set $interface up
	mv /etc/wpa_supplicant/wpa_supplicant.conf /etc/wpa_supplicant/wpa_supplicant.conf.orig
	echo "Ingrese nombre de la red"
	read red
	echo "Ingrese clave"
	read clave
	wpa_passphrase $red ""${clave}"" > /etc/wpa_supplicant/wpa_supplicant.conf
	wpa_supplicant -B -Dwext -i $interface -c /etc/wpa_supplicant/wpa_supplicant.conf
	dhcpcd $interface
else
		ip link set $interface up
		dhcpcd $interface
fi
if [ $resp = "1" ] 
then
	loadkeys es
	
	echo "Particionar disco duro-Instrucciones"
	echo "-------------------------------------"
	echo "1. La primera, /boot  .Se recomienda un tamaño de 100 MiB"
	echo "2. La segunda es la partición / (root). Su tamaño depende del uso que desees darle a ArchLinux."
	echo "3. La tercera es la partición /home, se recomienda asignarle un espacio considerable del disco duro."
	echo "4. Por último, la partición swap, se recomienda un tamaño de 1 o 2 GiB."
	echo "Notas:"
	echo "------"
	echo "a. Utilizar secuencia de órdenes: New » Primary | Logical » Size (en MB) » Beginning"
	echo "b. En el caso de la partición elegida como Swap, ir a la opción Type y seleccionar 82 (Linux Swap)"
	echo "c. En el caso de la partición elegida como /boot, seleccionar la opción Bootable"
	echo "d. Una vez seguros, debemos elegir la opción “Write“, y confirmar escribiendo “yes“, la escritura de la nueva tabla de particiones." 
	echo "   ¡Este procedimiento elimina todo el contenido previo del disco duro!"
	echo "e. Para salir de cfdisk, elegir Quit."
	echo "IMPORTANTE: Usar este orden de particiones y recordas en que device particiono (ej: /dev/sda)"
	echo "Para mas informacion leer ./Guias/Particionar.txt"
	echo "Presione tecla para continuar .."
	read tecla
	cfdisk
	
	echo "Ingrese el disco en cual particiono para instalar. Ej: si uso /dev/sda debe ingresar sda"
	read disco
	echo "Desea formatear /boot ? y/n"
	read r
	if [ $r = "y" ]
	then
	mkfs -t ext2 /dev/"${disco}"1
	fi
	echo "Desea formatear / ? y/n"
	read r
	if [ $r = "y" ]
	then
	mkfs -t ext4 /dev/"${disco}"2
	fi
	echo "Desea formatear /home ? y/n"
	read r
	if [ $r = "y" ]
	then	
	mkfs -t ext4 /dev/"${disco}"3
	fi
	mkswap /dev/"${disco}"4
	swapon /dev/"${disco}"4

	mount /dev/"${disco}"2 /mnt
	mkdir /mnt/boot
	mkdir /mnt/home
	mount /dev/"${disco}"1 /mnt/boot
	mount /dev/"${disco}"3 /mnt/home
	echo " "
	
	echo "Instalando el sistema base"
	
	pacman -Sy
	pacstrap /mnt base
	pacstrap /mnt base-devel
	pacstrap /mnt grub-bios
	pacstrap /mnt wireless_tools netcfg wpa_supplicant wpa_actiond ifplugd
	genfstab -U /mnt >> /mnt/etc/fstab
	
	echo "Configurando el sistema"
	arch-chroot /mnt echo "localhost" > /etc/hostname
	arch-chroot /mnt ln -s /usr/share/zoneinfo/America/Buenos_Aires /etc/localtime
	
	echo "Usted se encuentra en Argentina? y/n"
	read p
	if [ $p = "y" ]
	then
		echo "LANG=es_AR.UTF-8" > /mnt/etc/locale.conf
		echo "es_AR.UTF-8 UTF-8" >> /mnt/etc/locale.gen
		echo "es_AR ISO-8859-1" >> /mnt/etc/locale.gen
		echo "KEYMAP=es" > /mnt/etc/vconsole.conf

	else
		echo "Elija su preferencia de localizacion editando la linea LANG. En argentina: es_AR.UTF-8"
		arch-chroot /mnt nano /etc/locale.conf
		echo "Configurar localizacion: elimine el # al inicio de la linea de tu localizacion. En argentina: es_AR.UTF-8"
		echo "Presione una tecla para editar"
		read p
		arch-chroot /mnt nano /etc/locale.gen
	fi
	arch-chroot /mnt locale-gen
	echo "Configurar el teclado"
	echo "Usted quiere el teclado en español? y/n"
	echo "Si elige que no, debera configurarlo manualmente poniendo KEYMAP=idioma"
	read p
	if [ $p = "n" ]
	then
		arch-chroot /mnt nano /etc/vconsole.conf
	fi
	
	arch-chroot /mnt grub-install /dev/"${disco}"
	arch-chroot /mnt grub-mkconfig -o /boot/grub/grub.cfg
	
	arch-chroot /mnt mkinitcpio -p linux
	echo "Establesca la contraseña del root"
	arch-chroot /mnt passwd
	umount /mnt/{boot,home,}
	echo "IMPORTANTE: Se va a reiniciar el sistema, RECUERDE volver a ejecutar el script"$'\n'"asi se continua con la post-instalacion"$'\n'"SI NO SE REINICIA APRETE CTL+ALT+SUPR"
	echo "Presione una tecla para reiniciar"
	read r
	reboot
	
elif [ $resp = "2" ]
then
	echo "Post-Instalacion"
	
	echo "Actualizando el sistema"$'\n'
	echo "Su sistema es de 64 bits? y/n"
	read archi
	if [ $archi = "y" ]
	then
		echo "Desea agregar el repositorio Multilib? y/n"
		read multi
		if [ $multi = "y" ]
		then
			echo "[multilib]" $'\n'"SigLevel = PackageRequired" $'\n'"Include = /etc/pacman.d/mirrorlist" >> /etc/pacman.conf
		fi
	fi
	pacman -Syu
	
	echo "Creacion de usuario:"
	echo "Desea crear un usuario? y/n"
	read usr
	if [ $usr = "y" ]
	then
	echo "Ingrese nombre de usuario"
	read nombre
	useradd -m -g users -G audio,lp,optical,storage,video,wheel,games,power,scanner -s /bin/bash $nombre
	echo "Ingrese password"
	passwd $nombre	
	fi
	
	
	#Le falta sopa a partir de aca
	
	
	pacman -Sy pulseaudio-alsa alsa-plugins alsa-utils 
	
	pacman -Sy xorg-server xorg-xinit xorg-server-utils xf86-input-evdev xorg-twm xorg-xclock xterm ttf-dejavu dbus
	
	pacman -Sy mesa mesa-demos 
	
	echo "Tu placa de video es Nvidia? y/n"
	read video
	if [ $video = "y" ]
	then
		pacman -Sy nvidia 
	fi
	if [ $archi = "y" ]
	then
		pacman -Sy lib32-nvidia-utils lib32-alsa-plugins
	fi
	
	echo "Deseas instalar gnome como tu entorno grafico? y/n"
	read graf
	if [ $graf = "y" ]
	then
		pacman -Sy gnome gnome-extra
	fi
	echo "Post-Instalacion terminada"$'\n'"Presione tecla para reiniciar"
	read t	
	reboot
fi
#Le mando frula a las opciones y no entro por ninguna
echo "Ingreso una opcion no valida"


