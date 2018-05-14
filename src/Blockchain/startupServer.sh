#!/bin/sh
### BEGIN INIT INFO
# Provides: MiD Server Boot
# Required-Start:    $remote_fs $syslog
# Required-Stop:     $remote_fs $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6     
# Short-Description: Start mid server at boot time
# Description:       Start mid server at boot time
### END INIT INFO

# cd into where the hyperledger tools are installed
cd /home/cillian/fabric-tools;

./startFabric.sh;

# cd into where the blockchain code is
cd /home/cillian/mid/repo/src/Blockchain;

# run the installer
composer runtime install --card PeerAdmin@hlfv1 --businessNetworkName mid-network;

composer network start --card PeerAdmin@hlfv1 --networkAdmin admin --networkAdminEnrollSecret adminpw --archiveFile mid-network@0.0.1.bna --file networkadmin.card;

# generate rest server
composer-rest-server -c admin@mid-network -n never -w true;

exit 0;
