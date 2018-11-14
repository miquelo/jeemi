[![Build Status](https://travis-ci.com/miquelo/jeemi.svg?branch=master)](https://travis-ci.com/miquelo/jeemi)
[![Coverage](https://codecov.io/gh/miquelo/jeemi/branch/master/graph/badge.svg)](https://codecov.io/gh/miquelo/jeemi)

# Jakarta EE Mutable Infrastructure

Developers cares about artifacts. Operation developers cares about too many
mutable things.

This specification wants to give a continuous integration protocol where some
[Jakarta EE](https://jakarta.ee/) designers and programmers are responsible of
application building only, and nobody is worried about maintaining any execution
infrastructure. Its reference implementation tries to show how can it be done.

One of the most important points is that the deployment unit comes back to be an
artifact, which can be an EAR, WAR, RAR, JAR and so on. Infrastructure layer
ends at application container level. On other words, even a cloud based
infrastructure, a new software component version does not require a new machine
instance but a new artifact deployment on an existing managed infrastructure.

### Available agent images

_Coming soon..._

## Contributing

You can test the current snapshot of the specification and the reference
implementation by doing

```sh
$ sh setup.sh test
```

It ensures your contribution doesn't break any tested thing. We are humans, so
we can have bugs. Note that testing process can take a little bit time.

If you need a completely cleaned environment before testing, you can do

```sh
$ sh setup.sh clean
```

Maybe some generated files are not removed or restored after this operation. But
it tries to do it.
