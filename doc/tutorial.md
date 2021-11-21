# Tutorial

Let's create a library for melody composition that can be used as follows:

```java
new Melody().D().FSharp().D().A().play(); // Play D, F#, D, and A
```

The library we create allows only melodies that follows [the Pachelbel progression](http://openmusictheory.com/popRockHarmony-pachelbel.html). If a composed melody does not follow the progression, an error occurs at compile time:

```java
// No error
new Melody()
  .A().D().FSharp().D()
  .CSharp().A().E().A()
  .D().FSharp().B().FSharp()
  .CSharp().A().FSharp().A()
  .D().D().G().D()
  .FSharp().D().A().D()
  .G().D().B().G()
  .CSharp().A().E().A()
  .play();

// Type error at compile time
new Melody().A().B().C().play();
```

Since the users of our library are forced to follow the Pachelbel progression, they can compose *not-so-bad* melodies even if they have no idea about music theory.

## Create Gradle project

First of all, let's create a Gradle project for this tutorial:

```sh
# Create project directory
mkdir melodychain

# Move to project directory
cd melodychain

# Initialize project
gradle init \
  --dsl groovy \
  --type java-library \
  --test-framework junit-jupiter \
  --project-name melodychain \
  --package melodychain
```

See https://gradle.org if you don't know Gradle. See https://gradle.org/install/ if you haven't installed Gradle.

## Create AG file

An AG file is a file that defines valid method chains for a library. Silverchain compiles it into class definitions for that library.

Let's create the AG file for our library in `src/main/silverchain/melodychain.ag` with the following content:

```
melodychain.Melody {
  void
    ( D()      | FSharp() | A()      )[4] // Repeat D, F# or A four times
    ( A()      | CSharp() | E()      )[4]
    ( B()      | D()      | FSharp() )[4]
    ( FSharp() | A()      | CSharp() )[4]
    ( G()      | B()      | D()      )[4]
    ( D()      | FSharp() | A()      )[4]
    ( G()      | B()      | D()      )[4]
    ( A()      | CSharp() | E()      )[4]
    play(); // Play melody
}
```

The lines above define that the users can chain `D`, `FSharp`, or `A` four times, `A`, `CSharp`, or `E` four times, and so on. For more example AG files, please check [src/test/resources](../src/test/resources).

## Run Silverchain

Let's run the following command to generate class defintions from `src/main/silverchain/melodychain.ag`, the AG file we created in the previous section.

```sh
docker run -v $(pwd):/workdir --rm -it tomokinakamaru/silverchain:latest \
  --input src/main/silverchain/melodychain.ag \
  --output src/main/java
```

### No Docker?

If you can't use Docker in your environment, please build a jar from the source and run the build artifact with the options `--input src/main/silverchain/melodychain.ag` and `--output src/main/java`. See [README.md](../README.md) to learn how to build Silverchain from the source.

## Implement actions

Unfortunately, the generated files are not yet ready to use. They are just a skeleton of our library. We need to create the following two classes to make our library actually work:

```java
package melodychain;

public final class Melody extends Melody0Impl {
  public Melody() {
    super(new MelodyActionImpl());
  }
}
```

```java
package melodychain;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import java.util.ArrayList;
import java.util.List;

public class MelodyActionImpl implements MelodyAction {

  private final List<Integer> notes = new ArrayList<>();

  @Override
  public void A() {
    notes.add(69);
  }

  @Override
  public void D() {
    notes.add(62);
  }

  @Override
  public void FSharp() {
    notes.add(66);
  }

  @Override
  public void CSharp() {
    notes.add(61);
  }

  @Override
  public void E() {
    notes.add(64);
  }

  @Override
  public void B() {
    notes.add(71);
  }

  @Override
  public void G() {
    notes.add(67);
  }

  @Override
  public void play() {
    try {
      Sequence sequence = new Sequence(Sequence.PPQ, 1);
      Track track = sequence.createTrack();

      long offset = 4;
      for (int note : notes) {
        ShortMessage on = new ShortMessage();
        on.setMessage(ShortMessage.NOTE_ON, note, 127);

        ShortMessage off = new ShortMessage();
        off.setMessage(ShortMessage.NOTE_OFF, note, 0);

        track.add(new MidiEvent(on, offset));
        track.add(new MidiEvent(off, offset + 2));

        offset += 1;
      }

      Sequencer sequencer = MidiSystem.getSequencer(true);
      sequencer.open();
      sequencer.setSequence(sequence);
      sequencer.start();
      Thread.sleep(20000);
      sequencer.stop();
      sequencer.close();
    } catch (MidiUnavailableException | InvalidMidiDataException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
```

The first class `Melody` is an entrypoint class that will be instantiated by the library users. `Melody0Impl` is one of the generated classes, and `MelodyActionImpl` is the second class we create in this section.

The second class `MelodyActionImpl` defines the action of the generated methods. For instance, the lines in `MelodyActionImpl.A()` are executed when `A()` is invoked in a method chain. Since a melody violating the Pachelbel progression causes a type error, there is no need to check if a composed melody follows the progression at runtime.

## Compose melodies

Now, we can compose a melody with our library! To see if our library works as expected, let's create a test class and compose a melody by method chaining. Click the screenshot below to watch a demo.

[![Demo](http://i3.ytimg.com/vi/D2MHZajp_2M/maxresdefault.jpg)](https://youtu.be/D2MHZajp_2M)
