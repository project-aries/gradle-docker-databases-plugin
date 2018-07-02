### Version 0.9.2 (TBA)

### Version 0.9.1 (July 1, 2018)

* Bump `gradle-docker-applications-plugin` to `0.9.6`
* Added extension point `databases` with initial option `randomPorts` which if set to true will assign random ports to all exposed ports of dockerized application. Default is to expose ports exactly as they are to host.

### Version 0.9.0 (June 23, 2018)

* Initial release with support for `postgres`, `oracle`, `db2`, and `sqlserver`.
