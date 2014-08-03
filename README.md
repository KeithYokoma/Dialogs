# Dialogs

Simplify your dialog fragment implementations.

## Usage

### ProgressDialog

With builder.

```java
new ProgressDialog.Builder()
    .setTitle("Progress")
    .setMessage("NowLoading")
    .setCancelable(true)
    .create()
    .show(getFragmentManager());
```

### AlertDialog

## Download

Via Gradle.

```groovy
compile 'com.github.keithyokoma:Dialogs-Progress:1.0.0'
compile 'com.github.keithyokoma:Dialogs-Alert:1.0.0'
```

## License

```
Copyright (C) 2014 KeithYokoma. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
