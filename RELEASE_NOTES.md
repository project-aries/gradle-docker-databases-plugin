### Version 0.9.7 (TBA)

### Version 0.9.6 (July 27, 2018)
* Bump `gradle-docker-applications-plugin` to `1.1.0`

### Version 0.9.5 (July 19, 2018)
* Bump `gradle-docker-applications-plugin` to `1.0.2`

### Version 0.9.4 (July 16, 2018)
* Bump `gradle-docker-applications-plugin` to `1.0.1`
* Account for when plugin is applied to a script which is not the root script.

### Version 0.9.3 (July 15, 2018)
* Bump `gradle-docker-applications-plugin` to `1.0.0`

### Version 0.9.2 (July 7, 2018)
* Bump `gradle-docker-applications-plugin` to `0.9.7`

### Version 0.9.1 (July 1, 2018)

* Bump `gradle-docker-applications-plugin` to `0.9.6`
* Added extension point `databases` with initial option `randomPorts` which if set to true will assign random ports to all exposed ports of dockerized application. Default is to expose ports exactly as they are to host.

### Version 0.9.0 (June 23, 2018)

* Initial release with support for `postgres`, `oracle`, `db2`, and `sqlserver`.
