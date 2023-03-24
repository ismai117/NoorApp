import SwiftUI
import shared

struct ContentView: View {
	let greet = "Hello"

	var body: some View {
        Text(greet).foregroundColor(.blue)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
