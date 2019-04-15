# play-cas plugin

This plugin adds [CAS](https://en.wikipedia.org/wiki/Central_Authentication_Service) support to Play! Framework 1 applications.

# Features

# How to use

####  Add the dependency to your `dependencies.yml` file

```
require:
    - cas -> cas 1.2.0

repositories:
    - sismicsNexusRaw:
        type: http
        artifact: "https://nexus.sismics.com/repository/sismics/[module]-[revision].zip"
        contains:
            - cas -> *

```
####  Set configuration parameters

Add the following parameters to **application.conf**:

```
# CAS configuration
# ~~~~~~~~~~~~~~~~~~~~
cas.mock=false
cas.url=https://cas.example.com/cas
cas.service=http://service.example.com/login
```
####  Validate your ticket

```
String login = Cas.get().getValidationService().validate(ticket);
```

####  Mock the CAS server in dev

We recommand to mock CAS in development mode and test profile.

Use the following configuration parameter:

```
cas.mock=true
```

# License

This software is released under the terms of the Apache License, Version 2.0. See `LICENSE` for more
information or see <https://opensource.org/licenses/Apache-2.0>.
