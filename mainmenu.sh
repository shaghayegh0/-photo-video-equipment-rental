#!/bin/sh

# Function to display the Main Menu
MainMenu() {
    while [ "$CHOICE" != "START" ]
    do
        clear
        echo "================================================================="
        echo "| Oracle All Inclusive Tool"
        echo "| Main Menu - Select Desired Operation(s):"
        echo "| <CTRL-Z Anytime to Enter Interactive CMD Prompt>"
        echo "-----------------------------------------------------------------"
        echo " M) View Manual"
        echo " "
        echo " 1) Create Tables"
        echo " 2) Populate Tables"
        echo " 3) Query Tables"
	echo " 4) Delete Tables"
	echo " 5) Delete Sequences and Triggers"
	echo " 6) Create Sequences and Triggers"
    echo " 7) Advanced Query Tables"
	echo " "
        echo " X) Force/Stop/Kill Oracle DB"
        echo " "
        echo " E) End/Exit"
        echo "Choose: "
        read CHOICE

        case "$CHOICE" in
            "1") # Create Tables
                bash create_tables.sh
                Pause
                ;;
            "2") # Populate Tables
                bash populate_tables.sh
                Pause
                ;;
            "3") # Query Tables
                bash queries.sh
                Pause
                ;;
	    "4") # Delete Tables
		bash delete_tables.sh
		Pause
		;;
	    "5") # Delete Sequences and Triggers
                bash drop_sequence_triggers.sh
                Pause
                ;;
	    "6") # Create Sequences and Triggers
                bash create_sequence_triggers.sh
                Pause
                ;;
        "7") # Advanced Query Tables
                bash advanced_queries.sh
                Pause
                ;;
            "E") # Exit
                exit
                ;;
            *) 
                echo "Invalid option. Try again."
                ;;
        esac
    done
}

# Pause function
Pause() {
    echo "Press any key to continue..."
    read
}

# Program Entry Point
ProgramStart() {
    StartMessage
    while [ 1 ]
    do
        MainMenu
    done
}

ProgramStart

